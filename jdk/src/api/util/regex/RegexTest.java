package api.util.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/11/23 20:41
 */
public class RegexTest {
    public static void main(String[] args) {
        String str = "123(123)321";
        Pattern pattern = Pattern.compile("\\d+\\((\\d+)\\)\\d+");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            System.out.println("matcher = " + matcher.group(1));
        }
    }
}
