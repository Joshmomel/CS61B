import synthesizer.GuitarString;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);

    private static boolean checkInput(int i){
        return (i <= 37 && i >= 0);
    }

    public static void main(String[] args) {
        List<GuitarString> keys = new ArrayList<>();
        for (int i = 0; i < 37; i++) {
            double frequency = CONCERT_A * Math.pow(2, (i - 24) / 12.0);
            GuitarString guitarString = new GuitarString(frequency);
            keys.add(guitarString);
        }



        while (true) {
            while (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int i = keyboard.indexOf(key);
                System.out.println(i);
                if (checkInput(i)) {
                    GuitarString guitarString = keys.get(i);
                    guitarString.pluck();
                }
            }

            double sample = 0.0;
            for (GuitarString key : keys) {
                sample += key.sample();
            }

            StdAudio.play(sample);

            for (GuitarString key : keys) {
                key.tic();
            }

        }


    }



}
