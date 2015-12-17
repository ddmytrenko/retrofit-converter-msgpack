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

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.msgpack.MessagePack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit.Converter;

/**
 * @author Dmytro Dmytrenko
 */
public class MsgPackRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final String MSGPACK_MIME_TYPE = "application/x-msgpack";
    private static final MediaType MEDIA_TYPE = MediaType.parse(MSGPACK_MIME_TYPE);

    private final MessagePack messagePack;

    public MsgPackRequestBodyConverter(final MessagePack messagePack) {
        this.messagePack = messagePack;
    }

    @Override
    public RequestBody convert(final T value) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        messagePack.createPacker(out).write(value);
        return RequestBody.create(MEDIA_TYPE, out.toByteArray());
    }
}
