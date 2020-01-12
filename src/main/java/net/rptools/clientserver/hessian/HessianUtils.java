/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.rptools.clientserver.hessian;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import com.caucho.hessian.io.HessianOutput;

/**
 * @author drice
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class HessianUtils {
	public static final byte[] methodToBytes(String method, Object... parameters) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		HessianOutput hout = new HessianOutput(bout);
		hout.findSerializerFactory().setAllowNonSerializable(true);

		try {
			hout.call(method, parameters);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bout.toByteArray();
	}

	public static final byte[] methodToBytesGZ(String method, Object... parameters) {

		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		try {

			ByteArrayOutputStream hessianBytes = new ByteArrayOutputStream();
			HessianOutput hout = new HessianOutput(hessianBytes);
			hout.findSerializerFactory().setAllowNonSerializable(true);
			hout.call(method, parameters);

			GZIPOutputStream gzip = new GZIPOutputStream(bout);
			gzip.write(hessianBytes.toByteArray());
			gzip.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bout.toByteArray();
	}
}
