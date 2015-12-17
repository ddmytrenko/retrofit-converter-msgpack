/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Dmytro Dmytrenko, <dmytrenko.d@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.bitbucket.ddmytrenko.android.retrofit.converter.msgpack;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.msgpack.MessagePack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okio.Buffer;
import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.GET;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dmytro Dmytrenko
 */
public class MsgPackConverterFactoryTest {

    interface Message {
        int getId();
        String getTitle();
        String getContent();
    }

    @org.msgpack.annotation.Message
    static class MessageToTest implements Message {

        public int id;
        public String title;
        public String content;

        public MessageToTest() {
        }

        public MessageToTest(final int id, final String title, final String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        @Override
        public final int getId() {
            return this.id;
        }

        @Override
        public final String getTitle() {
            return this.title;
        }

        @Override
        public final String getContent() {
            return this.content;
        }

        @Override
        public final boolean equals(final Object other) {

            if (!(other instanceof MessageToTest)) {
                return false;
            }

            final Message msg = (MessageToTest) other;
            return (getId() == msg.getId())
                    && (getTitle().equals(msg.getTitle()))
                    && (getContent().equals(msg.getContent()));
        }
    }

    static final Message MOCK_UKRAINIAN = new MessageToTest(1, "Заголовок", "Зміст");
    static final Message MOCK_POLISH = new MessageToTest(2, "Tytuł", "Treść");

    interface Service {

        @GET("/message")
        Call<MessageToTest> getMessage();
    }

    @Rule
    public final MockWebServer server = new MockWebServer();

    private MessagePack messagePack;
    private Service service;

    private Buffer pack(final Object toPack) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        messagePack.createPacker(out).write(toPack);
        return new Buffer().readFrom(
                new ByteArrayInputStream(out.toByteArray())
        );
    }

    @Before
    public void setUp() {
        this.messagePack = new MessagePack();
        this.service = new Retrofit.Builder()
                .baseUrl(server.url(""))
                .addConverterFactory(MsgPackConverterFactory.create())
                .build()
                .create(Service.class);
    }

    @Test
    public void shouldGetAndReadMessageProperly() throws IOException {

        // Enqueue already packed Messages on the server
        server.enqueue(new MockResponse().setBody(pack(MOCK_UKRAINIAN)));
        server.enqueue(new MockResponse().setBody(pack(MOCK_POLISH)));

        // GET requests for the already enqueued Messages
        final Message firstMsg = service.getMessage().execute().body();
        final Message secondMsg = service.getMessage().execute().body();

        // Check
        assertThat(firstMsg).isEqualTo(MOCK_UKRAINIAN);
        assertThat(secondMsg).isEqualTo(MOCK_POLISH);
    }

}
