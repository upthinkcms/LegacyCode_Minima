// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.auth.cognito;

import com.amazonaws.services.cognitoidp.model.GroupType;
import com.amazonaws.services.cognitoidp.model.ListUsersInGroupResult;
import com.amazonaws.services.cognitoidp.model.ListUsersInGroupRequest;
import java.util.ArrayList;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Collection;
import java.util.Arrays;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersResult;
import com.amazonaws.services.cognitoidp.model.AdminRemoveUserFromGroupRequest;
import com.yojito.minima.auth.response.RemoveFromGroupResponse;
import com.yojito.minima.auth.request.RemoveFromGroupRequest;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import com.amazonaws.services.cognitoidp.model.AdminAddUserToGroupRequest;
import com.yojito.minima.auth.response.AddToGroupResponse;
import com.yojito.minima.auth.request.AddToGroupRequest;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.amazonaws.services.cognitoidp.model.AdminListGroupsForUserRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersRequest;
import com.amazonaws.services.cognitoidp.model.UserType;
import com.yojito.minima.auth.response.ListGroupsResult;
import com.yojito.minima.auth.request.GetUserGroupsRequest;
import com.amazonaws.services.cognitoidp.model.AdminDisableUserResult;
import com.amazonaws.services.cognitoidp.model.AdminDisableUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminEnableUserResult;
import com.amazonaws.services.cognitoidp.model.AdminEnableUserRequest;
import com.yojito.minima.auth.domain.AuthUser;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.yojito.minima.auth.domain.AuthUserLoginToken;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.yojito.minima.auth.response.TokenRefreshResponse;
import com.yojito.minima.auth.request.TokenRefreshRequest;
import java.util.concurrent.ExecutionException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.auth0.jwt.interfaces.Claim;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import com.yojito.minima.auth.domain.TokenValidation;
import com.google.common.cache.Cache;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.auth0.jwt.JWTVerifier;
import com.yojito.minima.logging.MinimaLogger;

public class Cognito
{
    private static final MinimaLogger a;
    private final String b;
    private final String c;
    private final JWTVerifier d;
    private final AWSCognitoIdentityProvider e;
    private Cache<String, TokenValidation> f;
    
    public Cognito(String clientId, final String userPoolId, final String idPoolId, final String region, final String groupRoleArn, final Optional<String> awsProfile) {
        this.f = (Cache<String, TokenValidation>)CacheBuilder.newBuilder().maximumSize(1000L).expireAfterWrite(10L, TimeUnit.MINUTES).build();
        this.b = clientId;
        this.c = userPoolId;
        clientId = String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", region, userPoolId);
        this.d = JWT.require(Algorithm.RSA256((RSAKeyProvider)new AwsCognitoRSAKeyProvider(clientId))).build();
        if (awsProfile.isPresent()) {
            this.e = (AWSCognitoIdentityProvider)((AWSCognitoIdentityProviderClientBuilder)((AWSCognitoIdentityProviderClientBuilder)AWSCognitoIdentityProviderClientBuilder.standard().withRegion(region)).withCredentials((AWSCredentialsProvider)new ProfileCredentialsProvider((String)awsProfile.get()))).build();
            Cognito.a.info("Creating cognito using aws profile %s in region %s", awsProfile.get(), region);
            return;
        }
        this.e = (AWSCognitoIdentityProvider)((AWSCognitoIdentityProviderClientBuilder)AWSCognitoIdentityProviderClientBuilder.standard().withRegion(region)).build();
    }
    
    public Cognito(final String clientId, final String userPoolId, final String idPoolId, final String region, final String groupRoleArn) {
        this(clientId, userPoolId, idPoolId, region, groupRoleArn, Optional.empty());
    }
    
    public TokenValidation validateToken(final String tokenInput) throws ExecutionException {
        final long currentTimeMillis = System.currentTimeMillis();
        final TokenValidation tokenValidation = (TokenValidation)this.f.get((Object)tokenInput, () -> {
            try {
                System.currentTimeMillis();
                this.d.verify(s);
                final DecodedJWT decodedJWT;
                decodedJWT.getClaims();
                final Map map;
                final Map map2;
                final String sub = map.containsKey("sub") ? map2.get("sub").asString() : null;
                final List groups = map2.containsKey("cognito:groups") ? map2.get("cognito:groups").asList((Class)String.class) : null;
                final String name = map2.containsKey("name") ? map2.get("name").asString() : null;
                final String email = map2.containsKey("email") ? map2.get("email").asString() : null;
                final String phoneNumber = map2.containsKey("phone_number") ? map2.get("phone_number").asString() : null;
                final String userName = map2.containsKey("username") ? map2.get("username").asString() : null;
                final Map<String, String> customAttributes = new HashMap<String, String>();
                map2.keySet().stream().filter(s2 -> s2.startsWith("custom:")).forEach(s3 -> map4.put(s3.substring(7), map3.get(s3).asString()));
                System.currentTimeMillis();
                if (Cognito.a.isDebugEnabled()) {
                    final long n;
                    final long n2;
                    Cognito.a.trace("Time taken jwtVerifier =" + (n - n2), new Object[0]);
                }
                return new TokenValidation(true, sub, groups, name, email, phoneNumber, userName, null, customAttributes);
            }
            catch (final AlgorithmMismatchException t) {
                Cognito.a.error((Throwable)t, "Validation failed", new Object[0]);
                return new TokenValidation(false, null, null, null, null, null, null, "Invalid Token", null);
            }
            catch (final SignatureVerificationException t2) {
                Cognito.a.error((Throwable)t2, "Validation failed", new Object[0]);
                return new TokenValidation(false, null, null, null, null, null, null, "Invalid Token", null);
            }
            catch (final TokenExpiredException t3) {
                Cognito.a.error((Throwable)t3, "Validation failed", new Object[0]);
                return new TokenValidation(false, null, null, null, null, null, null, "Expired Token", null);
            }
            catch (final InvalidClaimException t4) {
                Cognito.a.error((Throwable)t4, "Validation failed", new Object[0]);
                return new TokenValidation(false, null, null, null, null, null, null, "Invalid Token", null);
            }
        });
        final long currentTimeMillis2 = System.currentTimeMillis();
        if (Cognito.a.isDebugEnabled()) {
            Cognito.a.trace("Time taken for TokenValidation = %d, Validation = %s", currentTimeMillis2 - currentTimeMillis, tokenValidation.toStringPretty());
        }
        return tokenValidation;
    }
    
    public TokenRefreshResponse handleTokenRefresh(final TokenRefreshRequest tokenRefreshRequest) {
        try {
            final HashMap hashMap;
            (hashMap = new HashMap()).put("REFRESH_TOKEN", tokenRefreshRequest.getRefreshToken());
            final AdminInitiateAuthResult adminInitiateAuth = this.e.adminInitiateAuth(new AdminInitiateAuthRequest().withAuthFlow(AuthFlowType.REFRESH_TOKEN_AUTH).withAuthParameters((Map)hashMap).withClientId(this.b).withUserPoolId(this.c));
            if (Cognito.a.isInfoEnabled()) {
                Cognito.a.info("Incoming token : %s", tokenRefreshRequest.getRefreshToken());
                Cognito.a.info("Refresh Token result : %s", adminInitiateAuth);
            }
            final TokenRefreshResponse.Builder builder = TokenRefreshResponse.newBuilder();
            builder.setToken(AuthUserLoginToken.newBuilder().setAccessToken(adminInitiateAuth.getAuthenticationResult().getAccessToken()).setExpiresIn(adminInitiateAuth.getAuthenticationResult().getExpiresIn()).setIdToken(adminInitiateAuth.getAuthenticationResult().getIdToken()).setTokenType(adminInitiateAuth.getAuthenticationResult().getTokenType()).build());
            return builder.build();
        }
        catch (final Exception t) {
            Cognito.a.error(t, "Error while refreshing token", t);
            return TokenRefreshResponse.newBuilder().setError(true).setErroMessage((t.getMessage() == null) ? "NPE" : t.getMessage()).build();
        }
    }
    
    public boolean enableUser(final AuthUser user) {
        try {
            final AdminEnableUserResult adminEnableUser;
            return user != null && (adminEnableUser = this.e.adminEnableUser(new AdminEnableUserRequest().withUserPoolId(this.c).withUsername(user.getUsername()))) != null;
        }
        catch (final Exception t) {
            Cognito.a.error(t, "Error in enable user", new Object[0]);
            throw t;
        }
    }
    
    public boolean disableUser(final AuthUser user) {
        try {
            final AdminDisableUserResult adminDisableUser;
            return user != null && (adminDisableUser = this.e.adminDisableUser(new AdminDisableUserRequest().withUserPoolId(this.c).withUsername(user.getUsername()))) != null;
        }
        catch (final Exception t) {
            Cognito.a.error(t, "Error in disable user", new Object[0]);
            throw t;
        }
    }
    
    public ListGroupsResult listGroupsForUser(final GetUserGroupsRequest request) {
        String s;
        if ((s = request.getUserName()) == null) {
            s = this.e.listUsers(new ListUsersRequest().withFilter(String.format("email=\"%s\"", request.getEmail())).withUserPoolId(this.c)).getUsers().get(0).getUsername();
        }
        return new ListGroupsResult((List<String>)this.e.adminListGroupsForUser(new AdminListGroupsForUserRequest().withUserPoolId(this.c).withUsername(s)).getGroups().stream().map(groupType -> groupType.getGroupName()).collect(Collectors.toList()));
    }
    
    public AddToGroupResponse addUserToGroup(final AddToGroupRequest request) {
        try {
            final String groupName = request.getGroupName();
            String s;
            if ((s = request.getUserName()) == null) {
                s = this.e.listUsers(new ListUsersRequest().withFilter(String.format("email=\"%s\"", request.getEmail())).withUserPoolId(this.c)).getUsers().get(0).getUsername();
            }
            this.e.adminAddUserToGroup(new AdminAddUserToGroupRequest().withUserPoolId(this.c).withUsername(s).withGroupName(groupName));
            if (Cognito.a.isInfoEnabled()) {
                Cognito.a.info("Added user %s to group %s", request.getEmail(), groupName);
            }
            return new AddToGroupResponse(true);
        }
        catch (final ResourceNotFoundException t) {
            Cognito.a.error((Throwable)t, "Error in assigning group to the User. ".concat(t.getErrorMessage()), new Object[0]);
            return new AddToGroupResponse(false);
        }
        catch (final UserNotFoundException t2) {
            Cognito.a.error((Throwable)t2, "Error in assigning group to the User. ".concat(t2.getErrorMessage()), new Object[0]);
            return new AddToGroupResponse(false);
        }
    }
    
    public RemoveFromGroupResponse removeUserFromGroup(final RemoveFromGroupRequest request) {
        try {
            final String groupName = request.getGroupName();
            String s;
            if ((s = request.getUserName()) == null) {
                s = this.e.listUsers(new ListUsersRequest().withFilter(String.format("email=\"%s\"", request.getEmail())).withUserPoolId(this.c)).getUsers().get(0).getUsername();
            }
            if (this.e.adminRemoveUserFromGroup(new AdminRemoveUserFromGroupRequest().withUserPoolId(this.c).withUsername(s).withGroupName(groupName)) != null) {
                if (Cognito.a.isInfoEnabled()) {
                    Cognito.a.info("Removed user %s to group %s", request.getEmail(), groupName);
                }
                return new RemoveFromGroupResponse(true);
            }
            return new RemoveFromGroupResponse(false);
        }
        catch (final ResourceNotFoundException t) {
            Cognito.a.error((Throwable)t, String.format("Error in removing user from group %s - %s", request.getGroupName(), t.getErrorMessage()), new Object[0]);
            return new RemoveFromGroupResponse(false);
        }
        catch (final UserNotFoundException t2) {
            Cognito.a.error((Throwable)t2, String.format("Error in removing user from group %s -  %s", request.getGroupName(), t2.getErrorMessage()), new Object[0]);
            return new RemoveFromGroupResponse(false);
        }
    }
    
    public boolean existsUser(final String email) {
        final ListUsersResult listUsers;
        return (listUsers = this.e.listUsers(new ListUsersRequest().withFilter(String.format("email=\"%s\"", email)).withUserPoolId(this.c))).getUsers() != null && listUsers.getUsers().size() > 0;
    }
    
    public void setUserAttribute(final String email, String userName, final String attr, final String value) {
        try {
            if (userName == null) {
                userName = this.e.listUsers(new ListUsersRequest().withFilter(String.format("email=\"%s\"", email)).withUserPoolId(this.c)).getUsers().get(0).getUsername();
            }
            this.e.adminUpdateUserAttributes(new AdminUpdateUserAttributesRequest().withUserPoolId(this.c).withUsername(userName).withUserAttributes((Collection)Arrays.asList(new AttributeType().withName(attr).withValue(value))));
        }
        catch (final ResourceNotFoundException ex) {
            userName = "Error in removing user from group ".concat(ex.getErrorMessage());
            Cognito.a.error((Throwable)ex, userName, new Object[0]);
            throw new RuntimeException((Throwable)ex);
        }
        catch (final UserNotFoundException ex2) {
            userName = "Error in removing user from group. ".concat(ex2.getErrorMessage());
            Cognito.a.error((Throwable)ex2, userName, new Object[0]);
            throw new RuntimeException((Throwable)ex2);
        }
    }
    
    public void setUserAttributes(final String email, final List<Pair<String, String>> attributes) {
        try {
            this.e.adminUpdateUserAttributes(new AdminUpdateUserAttributesRequest().withUserPoolId(this.c).withUsername(this.e.listUsers(new ListUsersRequest().withFilter(String.format("email=\"%s\"", email)).withUserPoolId(this.c)).getUsers().get(0).getUsername()).withUserAttributes((Collection)attributes.stream().map(pair -> new AttributeType().withName((String)pair.getLeft()).withValue((String)pair.getRight())).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())));
        }
        catch (final ResourceNotFoundException ex) {
            Cognito.a.error((Throwable)ex, "Error in removing user from group ".concat(ex.getErrorMessage()), new Object[0]);
            throw new RuntimeException((Throwable)ex);
        }
        catch (final UserNotFoundException ex2) {
            Cognito.a.error((Throwable)ex2, "Error in removing user from group. ".concat(ex2.getErrorMessage()), new Object[0]);
            throw new RuntimeException((Throwable)ex2);
        }
    }
    
    public List<AuthUser> listGroupUsers(String group, final List<String> allGroups) {
        try {
            String nextToken = null;
            final ArrayList list = new ArrayList();
            do {
                final ListUsersInGroupResult listUsersInGroup = this.e.listUsersInGroup(new ListUsersInGroupRequest().withUserPoolId(this.c).withNextToken(nextToken).withGroupName(group));
                list.addAll(listUsersInGroup.getUsers());
                nextToken = listUsersInGroup.getNextToken();
                if (Cognito.a.isDebugEnabled()) {
                    Cognito.a.debug("Total Number of Users : %d, hasNextToken = %s, All users = %d", listUsersInGroup.getUsers().size(), listUsersInGroup.getNextToken(), list.size());
                }
            } while (nextToken != null);
            final Map map = (Map)list.stream().map(userType -> {
                AuthUser.newBuilder();
                if (Cognito.a.isDebugEnabled()) {
                    Cognito.a.debug("from attrs :: %s", userType.getAttributes().stream().map(attributeType -> attributeType.getName()).collect(Collectors.joining(",")));
                }
                final AuthUser.Builder builder;
                builder.setUsername(userType.getUsername());
                userType.getAttributes().forEach(attributeType2 -> {
                    if (attributeType2.getName().equals("phone_number")) {
                        builder2.setPhone_number(attributeType2.getValue());
                        return;
                    }
                    else if (attributeType2.getName().equals("email")) {
                        builder2.setEmail(attributeType2.getValue());
                        return;
                    }
                    else if (attributeType2.getName().equals("name")) {
                        builder2.setName(attributeType2.getValue());
                        return;
                    }
                    else if (attributeType2.getName().equals("sub")) {
                        builder2.setSub(attributeType2.getValue());
                        return;
                    }
                    else if (attributeType2.getName().startsWith("custom:")) {
                        builder2.addCustomAttribute(attributeType2.getName().substring(7), attributeType2.getValue());
                        return;
                    }
                    else {
                        Cognito.a.warn("Extra user attributes %s", attributeType2.getName());
                        return;
                    }
                });
                return Pair.of((Object)userType.getUsername(), (Object)builder);
            }).collect(Collectors.toMap(pair -> pair.getLeft(), pair2 -> pair2.getRight()));
            final HashMap hashMap = new HashMap();
            if (allGroups == null) {
                group = (String)Arrays.asList(group);
                return map.values().stream().map(builder3 -> builder3.setGroups(groups).build()).collect((Collector<? super Object, ?, List<AuthUser>>)Collectors.toList());
            }
            allGroups.forEach(s -> {
                if (Cognito.a.isDebugEnabled()) {
                    Cognito.a.debug("Retrieving user list for group %s", s);
                }
                final ArrayList list2 = new ArrayList();
                final String s2;
                do {
                    this.e.listUsersInGroup(new ListUsersInGroupRequest().withUserPoolId(this.c).withGroupName(s));
                    final ListUsersInGroupResult listUsersInGroupResult;
                    listUsersInGroupResult.getNextToken();
                    final ListUsersInGroupResult listUsersInGroupResult2;
                    list2.addAll(listUsersInGroupResult2.getUsers());
                    if (Cognito.a.isDebugEnabled()) {
                        Cognito.a.debug("%s Group Users Total Number of Users : %d, hasNextToken = %s, All users = %d", s, listUsersInGroupResult2.getUsers().size(), listUsersInGroupResult2.getNextToken(), list2.size());
                    }
                } while (s2 != null);
                if (Cognito.a.isDebugEnabled()) {
                    Cognito.a.debug("Retrieved user list for group %s, Total Users = %s", s, list2.size());
                }
                list2.forEach(userType2 -> {
                    List list3 = map2.get(userType2.getUsername());
                    final Object o3;
                    if (o3 == null) {
                        list3 = new ArrayList();
                        map2.put(userType2.getUsername(), list3);
                    }
                    list3.add(s3);
                });
                return;
            });
            hashMap.forEach((s4, groups2) -> {
                final AuthUser.Builder builder4 = map3.get(s4);
                final Object o4;
                if (o4 != null) {
                    builder4.setGroups(groups2);
                    return;
                }
                else {
                    Cognito.a.warn("Cant find user %s", s4);
                    return;
                }
            });
            return map.values().stream().map(builder5 -> builder5.addGroup(g).build()).collect((Collector<? super Object, ?, List<AuthUser>>)Collectors.toList());
        }
        catch (final Exception t) {
            Cognito.a.error(t, String.format("Error in listGroupUsers group -%s", t.getMessage()), new Object[0]);
            throw t;
        }
    }
    
    static {
        a = MinimaLogger.getLog(Cognito.class);
    }
}
