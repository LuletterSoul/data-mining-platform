package com.vero.dm.security;


import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.format.*;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:45 2017/7/23.
 * @since data-mining-platform
 */

public class UserPasswordServiceImpl implements UserPasswordService

{

    private static final Logger log = LoggerFactory.getLogger(UserPasswordServiceImpl.class);

    public static final String DEFAULT_HASH_ALGORITHM = "SHA-256";

    public static final int DEFAULT_HASH_ITERATIONS = 500000; // 500,000

    private SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private HashService hashService;

    private HashFormat hashFormat;

    private HashFormatFactory hashFormatFactory;

    private volatile boolean hashFormatWarned; // used to avoid excessive log noise

    public UserPasswordServiceImpl()
    {
        this.hashFormatWarned = false;

        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName(DEFAULT_HASH_ALGORITHM);
        hashService.setHashIterations(DEFAULT_HASH_ITERATIONS);
        hashService.setGeneratePublicSalt(true);
        // always want generated salts for user
        // passwords// to be most secure
        this.hashService = hashService;
        this.hashFormat = new Shiro1CryptFormat();
        this.hashFormatFactory = new DefaultHashFormatFactory();
    }

    public String encryptPassword(Object plaintext)
    {
        Hash hash = hashPassword(plaintext);
        checkHashFormatDurability();
        return this.hashFormat.format(hash);
    }

    public String encryptPasswordWithSalt(Object plaintext, Object customSalt)
    {
        Hash hash = hashPassword(plaintext, customSalt);
        checkHashFormatDurability();
        return this.hashFormat.format(hash);
    }

    public Hash hashPassword(Object plaintext)
    {
        ByteSource plaintextBytes = createByteSource(plaintext);
        if (plaintextBytes == null || plaintextBytes.isEmpty())
        {
            return null;
        }
        HashRequest request = createHashRequest(plaintextBytes);
        return hashService.computeHash(request);
    }

    public Hash hashPassword(Object plaintext, Object customSalt)
    {
        ByteSource plaintextBytes = createByteSource(plaintext);
        ByteSource saltBytes = createByteSource(customSalt);
        if (plaintextBytes == null || plaintextBytes.isEmpty())
        {
            return null;
        }
        HashRequest request = createHashRequestWithSalt(plaintextBytes, saltBytes);
        return hashService.computeHash(request);
    }

    protected void checkHashFormatDurability()
    {

        if (!this.hashFormatWarned)
        {

            HashFormat format = this.hashFormat;

            if (!(format instanceof ParsableHashFormat) && log.isWarnEnabled())
            {
                String msg = "The configured hashFormat instance [" + format.getClass().getName()
                             + "] is not a " + ParsableHashFormat.class.getName()
                             + " implementation.  This is "
                             + "required if you wish to support backwards compatibility for saved password checking (almost "
                             + "always desirable).  Without a "
                             + ParsableHashFormat.class.getSimpleName() + " instance, "
                             + "any hashService configuration changes will break previously hashed/saved passwords.";
                log.warn(msg);
                this.hashFormatWarned = true;
            }
        }
    }

    protected HashRequest createHashRequest(ByteSource plaintext)
    {
        return new HashRequest.Builder().setSource(plaintext).build();
    }

    protected HashRequest createHashRequestWithSalt(ByteSource plaintext, ByteSource salt)
    {

        return new HashRequest.Builder().setSource(plaintext).setSalt(salt).build();
    }

    protected ByteSource createByteSource(Object o)
    {
        return ByteSource.Util.bytes(o);
    }

    public boolean passwordsMatch(Object plaintext, Hash saved)
    {
        ByteSource plaintextBytes = createByteSource(plaintext);

        if (saved == null || saved.isEmpty())
        {
            return plaintextBytes == null || plaintextBytes.isEmpty();
        }
        else
        {
            if (plaintextBytes == null || plaintextBytes.isEmpty())
            {
                return false;
            }
        }

        HashRequest request = buildHashRequest(plaintextBytes, saved);

        Hash computed = this.hashService.computeHash(request);

        return saved.equals(computed);
    }

    public boolean passwordsMatch(Object submittedPlaintext, String saved)
    {
        ByteSource plaintextBytes = createByteSource(submittedPlaintext);
        Boolean isEqualWhenEmpty = checkEmptyLogicEqualization(saved, plaintextBytes);
        if (isEqualWhenEmpty != null) return isEqualWhenEmpty;
        // First check to see if we can reconstitute the original hash - this allows us to
        // perform password hash comparisons even for previously saved passwords that don't
        // match the current HashService configuration values. This is a very nice feature
        // for password comparisons because it ensures backwards compatibility even after
        // configuration changes.
        HashFormat discoveredFormat = this.hashFormatFactory.getInstance(saved);
        Boolean savedHash = hashParsablePassword(submittedPlaintext, saved, discoveredFormat);
        if (savedHash != null) return savedHash;

        // If we're at this point in the method's execution, We couldn't reconstitute the original
        // hash.
        // So, we need to hash the submittedPlaintext using current HashService configuration and
        // then
        // compare the formatted output with the saved string. This will correctly compare
        // passwords,
        // but does not allow changing the HashService configuration without breaking previously
        // saved
        // passwords:

        // The saved text value can't be reconstituted into a Hash instance. We need to format the
        // submittedPlaintext and then compare this formatted value with the saved value:
        HashRequest request = createHashRequest(plaintextBytes);
        Hash computed = this.hashService.computeHash(request);
        String formatted = this.hashFormat.format(computed);

        return saved.equals(formatted);
    }

    public boolean passwordMatch(Object submittedPlaintext, String saved, ByteSource salt)
    {
        ByteSource plaintextBytes = createByteSource(submittedPlaintext);
        Boolean isEqualWhenEmpty = checkEmptyLogicEqualization(saved, plaintextBytes);
        if (isEqualWhenEmpty != null) return isEqualWhenEmpty;
        HashRequest request = createHashRequestWithSalt(plaintextBytes, salt);
        Hash computed = this.hashService.computeHash(request);
        String formatted = this.hashFormat.format(computed);
        return saved.equals(formatted);
    }

    private Boolean hashParsablePassword(Object submittedPlaintext, String saved,
                                         HashFormat discoveredFormat)
    {
        if (discoveredFormat != null && discoveredFormat instanceof ParsableHashFormat)
        {

            ParsableHashFormat parsableHashFormat = (ParsableHashFormat)discoveredFormat;
            Hash savedHash = parsableHashFormat.parse(saved);

            return passwordsMatch(submittedPlaintext, savedHash);
        }
        return null;
    }

    private Boolean checkEmptyLogicEqualization(String saved, ByteSource plaintextBytes)
    {
        if (saved == null || saved.length() == 0)
        {
            return plaintextBytes == null || plaintextBytes.isEmpty();
        }
        else
        {
            if (plaintextBytes == null || plaintextBytes.isEmpty())
            {
                return false;
            }
        }
        return null;
    }

    protected HashRequest buildHashRequest(ByteSource plaintext, Hash saved)
    {
        // keep everything from the saved hash except for the source:
        return new HashRequest.Builder().setSource(plaintext)
            // now use the existing saved data:
            .setAlgorithmName(saved.getAlgorithmName()).setSalt(saved.getSalt()).setIterations(
                saved.getIterations()).build();
    }

    public HashService getHashService()
    {
        return hashService;
    }

    public void setHashService(HashService hashService)
    {
        this.hashService = hashService;
    }

    public HashFormat getHashFormat()
    {
        return hashFormat;
    }

    public void setHashFormat(HashFormat hashFormat)
    {
        this.hashFormat = hashFormat;
    }

    public HashFormatFactory getHashFormatFactory()
    {
        return hashFormatFactory;
    }

    public void setHashFormatFactory(HashFormatFactory hashFormatFactory)
    {
        this.hashFormatFactory = hashFormatFactory;
    }

    public ByteSource generateRandomSalt(int numBytes)
    {
        return randomNumberGenerator.nextBytes(numBytes);
    }

}
