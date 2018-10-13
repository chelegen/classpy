package com.github.zxh.classpy.bitcoin;

import com.github.zxh.classpy.bitcoin.types.*;
import com.github.zxh.classpy.common.FileComponent;

import java.util.function.Supplier;

public class BlockComponent extends FileComponent {

    public final void read(BlockReader reader) {
        try {
            int offset = reader.getPosition();
            readContent(reader);
            int length = reader.getPosition() - offset;
            super.setOffset(offset);
            super.setLength(length);
        } catch (Exception e) {
            System.out.println("error parsing: " + getClass());
            throw e;
        }
    }

    protected void readContent(BlockReader reader) {
        for (FileComponent fc : getComponents()) {
            ((BlockComponent) fc).read(reader);
        }
    }

    protected long readVarInt(BlockReader reader, String name) {
        VarInt varInt = new VarInt();
        add(name, varInt);
        varInt.read(reader);
        return varInt.getValue();
    }

    protected void uint32(String name) {
        add(name, new UInt32());
    }

    protected void uint64(String name) {
        add(name, new UInt64());
    }

    protected void hash(String name) {
        add(name, new Hash());
    }

    protected void bytes(String name, int n) {
        add(name, new Bytes(n));
    }

    protected void script(String name) {
        add(name, new Script());
    }

    protected void table(String name,
                         Supplier<? extends BlockComponent> supplier) {
        add(name, new Table(supplier));
    }

}
