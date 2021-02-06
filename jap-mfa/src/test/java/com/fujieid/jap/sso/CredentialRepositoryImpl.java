package com.fujieid.jap.sso;

import com.warrenstrange.googleauth.ICredentialRepository;

import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
public class CredentialRepositoryImpl implements ICredentialRepository {

    public CredentialRepositoryImpl(){
    }

    /**
     * This method retrieves the Base32-encoded private key of the given user.
     *
     * @param userName the user whose private key shall be retrieved.
     * @return the private key of the specified user.
     */
    @Override
    public String getSecretKey(String userName) {
        return null;
    }

    /**
     * This method saves the user credentials.
     *
     * @param userName       the user whose data shall be saved.
     * @param secretKey      the generated key.
     * @param validationCode the validation code.
     * @param scratchCodes   the list of scratch codes.
     */
    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {

    }
}
