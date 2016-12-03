package data;

/**
 * Created by Jude Hokyoon Woo on 12/3/2016.
 */
public class Hash {
    /**
     *
     * @param txt, text in plain format
     * @param hashType MD5 OR SHA1
     * @return hash in hashType
     */
    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(String.format("%02x", array[i]));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("No such ALGORITHM");
        }
        return null;
    }

    public static String md5(String txt) {
        return Hash.getHash(txt, "MD5");
    }

    public static String sha1(String txt) {
        return Hash.getHash(txt, "SHA1");
    }
}
