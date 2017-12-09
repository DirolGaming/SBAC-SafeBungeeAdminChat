package me.dirolgaming.safebac;

/**
 * @author Mark Vainomaa
 */
final class Utils {
    static String buildMessage(String[] args) {
        StringBuilder acb = new StringBuilder();
        for(String arg : args)
            acb.append(arg).append(" ");
        return acb.toString();
    }
}
