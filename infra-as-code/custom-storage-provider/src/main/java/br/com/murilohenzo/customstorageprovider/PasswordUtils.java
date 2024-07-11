package br.com.murilohenzo.customstorageprovider;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

public class PasswordUtils {

    public static boolean verifyPassword(String bcryptString, String password) {
        return OpenBSDBCrypt.checkPassword(bcryptString, password.toCharArray());
    }

}
