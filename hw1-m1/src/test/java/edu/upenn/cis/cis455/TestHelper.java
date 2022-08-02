package edu.upenn.cis.cis455;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import edu.upenn.cis.cis455.TestHelper;
import edu.upenn.cis.cis455.exceptions.HaltException;
import edu.upenn.cis.cis455.m1.handling.HttpIoHandler;
import edu.upenn.cis.cis455.m1.server.HttpListener;
import edu.upenn.cis.cis455.m1.server.HttpResponse;
import edu.upenn.cis.cis455.m1.server.HttpWorker;
import edu.upenn.cis.cis455.m1.server.ThreadPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class TestHelper {
    
//    public static Socket getMockSocket(String socketContent, ByteArrayOutputStream output) throws IOException {
//        Socket s = mock(Socket.class);
//        byte[] arr = socketContent.getBytes();
//        final ByteArrayInputStream bis = new ByteArrayInputStream(arr);
//
//        when(s.getInputStream()).thenReturn(bis);
//        when(s.getOutputStream()).thenReturn(output);
//        when(s.getLocalAddress()).thenReturn(InetAddress.getLocalHost());
//        when(s.getRemoteSocketAddress()).thenReturn(InetSocketAddress.createUnresolved("host", 8080));
//        
//        return s;
//    }
	
	@Test
	public void testThreadPool() {
		ThreadPool pool = new ThreadPool(0, "", null);
		assertTrue(pool.running);
	}
			
	@Test
	public void testWorker() {
		ThreadPool pool = new ThreadPool(0, "", null);
		HttpWorker w = new HttpWorker(pool);
		assertTrue(w.running);
		w.shutdown();
		assertFalse(w.running);
	}
	
	@Test
	public void testResponse() {
		HttpResponse response = new HttpResponse();
		byte[] body = "This is the body".getBytes();
		response.bodyRaw(body);
		response.type("html");
		assertEquals("html", response.type());
		assertEquals("This is the body", response.body());
		assertEquals(body.length, response.bodyLength());
	}
	
	@Test
	public void testHaltException() {
		HaltException except = new HaltException(404, "Files Does Note Exist");
		assertEquals(404, except.statusCode());
		assertEquals("Files Does Note Exist", except.body());
	}
		
	
	@Test
	public void testListener() {
		HttpListener listener = new HttpListener(45555, new ThreadPool(0, "", null));
		assertEquals(45555, listener.port);
		assertTrue(listener.running);
		listener.shutdown();
		assertFalse(listener.running);
	}
	
}
