package labcse;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;

import javax.crypto.Cipher;

public class Test {
	public static void main(final String[] args) {
        final Provider[] providers = Security.getProviders();
        for (int i = 0; i < providers.length; i++) {
            final String name = providers[i].getName();
            final double version = providers[i].getVersion();
            System.out.println("Provider[" + i + "]:: " + name + " " + version);
            if (args.length > 0) {
                final Iterator it = providers[i].keySet().iterator();
                while (it.hasNext()) {
                    final String element = (String) it.next();
                    if (element.toLowerCase().startsWith(args[0].toLowerCase())
                            || args[0].equals("-all"))
                        System.out.println("\t" + element);
                }
            }
        }
        try {
			System.out.println(Cipher.getMaxAllowedKeyLength("AES"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
