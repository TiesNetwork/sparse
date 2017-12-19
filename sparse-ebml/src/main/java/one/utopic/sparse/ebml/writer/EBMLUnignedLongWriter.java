/**
 * Copyright © 2017 Anton Filatov (ya-enot@mail.ru)
 *
 * This file is part of SParse.
 *
 * SParse is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SParse is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SParse.  If not, see <https://www.gnu.org/licenses/lgpl-3.0>.
 */
package one.utopic.sparse.ebml.writer;

import java.io.IOException;

import one.utopic.sparse.ebml.EBMLFormatter;
import one.utopic.sparse.ebml.EBMLType;
import one.utopic.sparse.ebml.EBMLWriter;
import one.utopic.sparse.ebml.util.ByteArrayInput;

import static one.utopic.sparse.ebml.util.EBMLHelper.*;

public class EBMLUnignedLongWriter implements EBMLWriter<EBMLFormatter, Long> {

    private final EBMLType type;

    public EBMLUnignedLongWriter(EBMLType type) {
        this.type = type;
    }

    public Part<EBMLFormatter> prepare(final Long o) throws IOException {
        if (o < 0) {
            throw new IOException("Unsigned long value write as negative");
        }
        return o == null ? null : new Part<EBMLFormatter>() {

            private final byte[] data = longToBytes(o);

            public void write(EBMLFormatter formatter) throws IOException {
                formatter.newHeader(type, data.length);
                formatter.write(new ByteArrayInput(data));
                formatter.next();
            }

            public int getSize(EBMLFormatter formatter) throws IOException {
                return formatter.getHeaderSize(type, data.length) + data.length;
            }

        };
    }

}
