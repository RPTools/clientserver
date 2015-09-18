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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.caucho.hessian.io.HessianInput;

/**
 * @author drice
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractMethodHandler implements MethodHandler {

	/* (non-Javadoc)
	 * @see clientserver.simple.MessageHandler#handleMessage(java.lang.String, byte[])
	 */
	public void handleMessage(String id, byte[] message) {
		try {
			HessianInput in = null;
			try {
				in = new HessianInput(new GZIPInputStream(new ByteArrayInputStream(message)));
			} catch (IOException ioe) {
				in = new HessianInput(new ByteArrayInputStream(message));
			}
			in.startCall();
			List<Object> arguments = new ArrayList<Object>();
			while (!in.isEnd()) {
				arguments.add(in.readObject());
			}
			in.completeCall();

			handleMethod(id, in.getMethod(), arguments.toArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
