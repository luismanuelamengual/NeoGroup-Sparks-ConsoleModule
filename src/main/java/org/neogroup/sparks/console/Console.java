
package org.neogroup.sparks.console;

import java.io.*;

/**
 * Console Manager
 */
public class Console {

    private boolean closed;
    private final BufferedReader reader;
    private final PrintWriter writer;

    /**
     * Constructor for the console
     * @param in input stream
     * @param out output stream
     */
    public Console (InputStream in, OutputStream out) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.writer = new PrintWriter(out);
        closed = false;
    }

    /**
     * Get a buffered reader for the console
     * @return buffered reader
     */
    public BufferedReader getReader() {
        return reader;
    }

    /**
     * Get a print writer of the console
     * @return print writer
     */
    public PrintWriter getWriter() {
        return writer;
    }

    /**
     * Closes the console
     * @return console
     */
    public Console close()  {
        if (!closed) {
            try { reader.close(); } catch (Exception ex) {}
            try { writer.close(); } catch (Exception ex) {}
            closed = true;
        }
        return this;
    }

    /**
     * Indicates if the console connection is closed
     * @return boolean
     */
    public boolean isClosed () {
        if (!closed && writer.checkError()) {
            close();
        }
        return closed;
    }

    /**
     * Reads a line from the console
     * @return string
     */
    public String readLine()  {
        String line = null;
        try { line = reader.readLine(); } catch (Exception ex) { close(); }
        return line;
    }

    /**
     * Checks if the consle is ready to read
     * @return boolean
     */
    public boolean ready() {
        boolean ready = false;
        try { ready = reader.ready(); } catch (IOException e) { close(); }
        return ready;
    }

    /**
     * Reads a character
     * @return int
     */
    public int read() {
        int read = 0;
        try { read = reader.read(); } catch (IOException e) { close(); }
        return read;
    }

    /**
     * Reads a buffer from the console
     * @param cbuf buffer
     * @param off offset
     * @param len length
     * @return int
     */
    public int read(char[] cbuf, int off, int len) {
        int read = 0;
        try { read = reader.read(cbuf, off, len); } catch (IOException e) { close(); }
        return read;
    }

    /**
     * Flushes the content to the console
     * @return console
     */
    public Console flush() {
        writer.flush();
        return this;
    }

    /**
     * Writes content to the console
     * @param buf
     * @return
     */
    public Console write(String buf) {
        writer.write(buf);
        return this;
    }

    /**
     * Writes content to the console
     * @param c
     * @return
     */
    public Console write(int c) {
        writer.write(c);
        return this;
    }

    /**
     * Writes content to the console
     * @param cbuf
     * @param off
     * @param len
     * @return
     */
    public Console write(char[] cbuf, int off, int len) {
        writer.write(cbuf, off, len);
        return this;
    }

    /**
     * Writes content to the console
     * @param s
     * @param off
     * @param len
     * @return
     */
    public Console write(String s, int off, int len) {
        writer.write(s, off, len);
        return this;
    }

    /**
     * Prints a new line
     * @return console
     */
    public Console newLine() {
        writer.println();
        return this;
    }

    /**
     * Prints a boolean
     * @param b
     */
    public Console print(boolean b) {
        writer.print(b);
        return this;
    }

    /**
     * Prints a character
     * @param c
     */
    public Console print(char c) {
        writer.print(c);
        return this;
    }

    /**
     * Prints an integer
     * @param i
     */
    public Console print(int i) {
        writer.print(i);
        return this;
    }

    /**
     * Prints a long
     * @param l
     */
    public Console print(long l) {
        writer.print(l);
        return this;
    }

    /**
     * Prints a float
     * @param f
     */
    public Console print(float f) {
        writer.print(f);
        return this;
    }

    /**
     * Prints a double
     * @param d
     */
    public Console print(double d) {
        writer.print(d);
        return this;
    }

    /**
     * Prints an array of characters
     * @param s
     */
    public Console print(char[] s) {
        writer.print(s);
        return this;
    }

    /**
     * Prints a string
     * @param s
     */
    public Console print(String s) {
        writer.print(s);
        return this;
    }

    /**
     * Prints an object
     * @param obj
     */
    public Console print(Object obj) {
        writer.print(obj);
        return this;
    }

    /**
     * Prints a new line
     */
    public Console println() {
        writer.println();
        return this;
    }

    /**
     * Prints a boolean
     * @param x
     */
    public Console println(boolean x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints a character
     * @param x
     */
    public Console println(char x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints an integer
     * @param x
     */
    public Console println(int x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints a long
     * @param x
     */
    public Console println(long x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints a float
     * @param x
     */
    public Console println(float x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints a double
     * @param x
     */
    public Console println(double x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints an array of characters
     * @param x
     */
    public Console println(char[] x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints a string
     * @param x
     */
    public Console println(String x) {
        writer.println(x);
        return this;
    }

    /**
     * Prints an object
     * @param x
     */
    public Console println(Object x) {
        writer.println(x);
        return this;
    }
}
