package com.jeong.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BmpData {
    private byte[] part1BMP;
    private byte[] part2BMP;

    public BmpData(byte[] part1BMP, byte[] part2BMP) {
        this.part1BMP = part1BMP;
        this.part2BMP = part2BMP;
    }

    public byte[] getPartNBmp(String partN) {
        if (partN.equals("part1"))
            return this.part1BMP;
        else if (partN.equals("part2"))
            return this.part2BMP;
        return this.part2BMP;
    }

}
