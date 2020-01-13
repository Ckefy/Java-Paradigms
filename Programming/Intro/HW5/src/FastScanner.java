import java.io.*;
import java.util.ArrayList;

public class FastScanner {
    private byte[] fileInArray, buffer;
    private InputStream stream = System.in;
    private int pcolumn, pos = 0, len;
    private String[] splited;
    private boolean flagEOF = false;
    private ArrayList<Integer> bytesList;

    public FastScanner() throws IOException {
        readFastScannerStream();
    }

    public FastScanner(String s) throws IOException {
        stream = new FileInputStream(s);
        readFastScannerStream();
    }

    private int readBytes() throws IOException {
        if (pos < len)
            return buffer[pos++];

        do {
            len = stream.read(buffer);
        } while (len == 0);
        pos = 0;
        return len == -1 ? -1 : buffer[pos++];
    }

    private void readFastScannerStream() throws IOException, ArrayIndexOutOfBoundsException {
        bytesList = new ArrayList<Integer>();
        if (pos == 0)
            buffer = new byte[1024 * 12 * 8];
        int a = readBytes();
        while (a != -1 && a != '\n') {
            if (a == '\r') {
                a = readBytes();
                continue;
            }
            bytesList.add(a);
            do {
                a = readBytes();
            } while (a == 0);
        }
        if (a == -1)
            flagEOF = true;
        fileInArray = new byte[bytesList.size()];
        for (int i = 0; i < bytesList.size(); i++) {
            if (fileInArray[i] == '\r')
                break;
            fileInArray[i] = (byte) ((int) bytesList.get(i));
        }
        spliting();
    }

    private void spliting() throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException {
        String st = new String(fileInArray, "UTF-8");
        splited = st.split(" ");
        pcolumn = 0;
    }

    public boolean hasNextLine() {
        return !flagEOF || bytesList.size() != 0;
    }

    public boolean needChangeLine() throws ArrayIndexOutOfBoundsException, IOException {
        if (pcolumn >= splited.length) {
            pcolumn = 0;
            readFastScannerStream();
            return true;
        } else {
            return false;
        }
    }

    public String nextElement() throws ArrayIndexOutOfBoundsException {
        pcolumn++;
        return splited[pcolumn - 1];
    }
}

