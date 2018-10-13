package com.github.zxh.classpy.gui.support;

import java.net.URL;
import java.util.Arrays;

public class FileTypeInferer {

    private static final byte[] wasmMagicNumber = {0, 'a', 's', 'm'};
    private static final byte[] binaryChunkSig = {0x1B, 'L', 'u', 'a'};
    private static final byte[] classMagicNumber = {
            (byte) 0xCA,
            (byte) 0xFE,
            (byte) 0xBA,
            (byte) 0xBE
    };

    public static FileType inferFileType(URL url) {
        String filename = url.toString().toLowerCase();
        if (filename.startsWith("https://blockchain.info/rawblock")) {
            return FileType.BITCOIN_BLOCK;
        }
        if (filename.endsWith(".jar")) {
            return FileType.JAVA_JAR;
        }
        if (filename.endsWith(".class")) {
            return FileType.JAVA_CLASS;
        }
        if (filename.endsWith(".luac")) {
            return FileType.LUA_BC;
        }
        if (filename.endsWith(".wasm")) {
            return FileType.WASM;
        }
        return FileType.UNKNOWN;
    }

    public static FileType inferFileType(byte[] data) {
        if (data.length >= 4) {
            byte[] magicNumber = Arrays.copyOf(data, 4);
            if (Arrays.equals(magicNumber, classMagicNumber)) {
                return FileType.JAVA_CLASS;
            }
            if (Arrays.equals(magicNumber, binaryChunkSig)) {
                return FileType.LUA_BC;
            }
            if (Arrays.equals(magicNumber, wasmMagicNumber)) {
                return FileType.WASM;
            }
        }
        return FileType.UNKNOWN;
    }

}
