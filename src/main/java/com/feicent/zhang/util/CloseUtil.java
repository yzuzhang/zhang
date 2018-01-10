package com.feicent.zhang.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class CloseUtil {

	/**
	 * 关闭资源
	 * @param closeables
	 */
	public static void close(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

	public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

	/**
	 * 关闭soccket
	 * @param socket
	 */
	public static void closeSocket(final Socket socket){
		if(socket != null){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
