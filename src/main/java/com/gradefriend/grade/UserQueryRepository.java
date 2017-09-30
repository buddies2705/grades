package com.gradefriend.grade;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author batman
 * @since 24/9/17
 */


public interface UserQueryRepository extends MongoRepository<UserQuery, String> {
}
