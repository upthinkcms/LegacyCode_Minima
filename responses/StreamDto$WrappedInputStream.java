// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.responses;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;

public static class WrappedInputStream extends InputStream
{
    private final InputStream inputStream;
    
    public WrappedInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) throws IOException {
        System.out.printf(">>>> READ ARRAY and len1..off = %d, len = %d\n", off, len);
        return this.inputStream.read(b, off, len);
    }
    
    @Override
    public byte[] readAllBytes() throws IOException {
        System.out.println(">>>> READ ALL BYTES ..");
        return this.inputStream.readAllBytes();
    }
    
    @Override
    public byte[] readNBytes(final int len) throws IOException {
        System.out.println(">>>> READ BYTES ..");
        return this.inputStream.readNBytes(len);
    }
    
    @Override
    public int readNBytes(final byte[] b, final int off, final int len) throws IOException {
        System.out.printf(">>>> READ ARRAY and len2..off = %d, len = %d\n", off, len);
        return this.inputStream.readNBytes(b, off, len);
    }
    
    @Override
    public long skip(final long n) throws IOException {
        System.out.println(">>>> SKIP ..");
        return this.inputStream.skip(n);
    }
    
    @Override
    public synchronized void mark(final int readlimit) {
        System.out.println(">>>> MARK ..");
        this.inputStream.mark(readlimit);
    }
    
    @Override
    public synchronized void reset() throws IOException {
        System.out.println(">>>> RESET ..");
        this.inputStream.reset();
    }
    
    @Override
    public boolean markSupported() {
        System.out.println(">>>> MARKSUPPORTED..");
        return this.inputStream.markSupported();
    }
    
    @Override
    public long transferTo(final OutputStream out) throws IOException {
        System.out.println(">>>> TRANSFERTO ..");
        return this.inputStream.transferTo(out);
    }
    
    @Override
    public int available() throws IOException {
        final int available = this.inputStream.available();
        System.out.println(">>>> AVAILABLESTREAM .." + available);
        return available;
    }
    
    @Override
    public int read(final byte[] b) throws IOException {
        final int read = this.inputStream.read(b);
        System.out.println(">>>> READING BYTE STREAM .." + read);
        return read;
    }
    
    @Override
    public void close() throws IOException {
        System.out.println(">>>> CLOSING STREAM ..");
        this.inputStream.close();
    }
    
    @Override
    public int read() throws IOException {
        final int read = this.inputStream.read();
        System.out.println(">>>> READING STREAM .." + read);
        return read;
    }
}
