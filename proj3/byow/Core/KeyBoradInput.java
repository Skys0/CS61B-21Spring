package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

/**
 * 这个类主要来等待用户在 keyborad 输入，以及处理成对应的数据
  */
public class KeyBoradInput {
    /** 专门处理一个字符的输入的 */
    public static char InputSingleWord() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                System.out.println(x);
                return x;
            }
        }
    }

    public static Long InputSeed() {
        StringBuilder s = new StringBuilder(new String());
        MainMenu.FlashSeedMenu(0);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                if (x == 'S' || x == 's')   break;
                s.append(x);
                MainMenu.FlashSeedMenu(Long.parseLong(s.toString()));
            }
        }
        return Long.parseLong(s.toString());
    }
}
