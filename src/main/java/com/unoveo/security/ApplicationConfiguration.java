package com.unoveo.security;

import com.unoveo.hibernate.HibernateUtil;
import com.unoveo.jwt.JwtTokenFilter;
import com.unoveo.jwt.JwtTokenProvider;
import com.unoveo.jwt.MyUserDetails;
import com.unoveo.model.AppUser;
import com.unoveo.repository.UserRepository;
import com.unoveo.service.UserService;
import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@ComponentScan
public class ApplicationConfiguration {

        @Bean
    public JwtTokenProvider getJwtTokenProvider(){
        return new JwtTokenProvider();
    }

//    @Bean
//    public MyUserDetails getuserDetails(){
//        return new MyUserDetails();
//    }

    @Bean
    public UserRepository getUserRepository(){
        return new UserRepository() {
            @Override
            public boolean existsByUsername(String username) {
                return false;
            }

            @Override
            public AppUser findByUsername(String username) {

                System.out.println("in application configuration >>>>>>>>> findByUsername ");


                   Session session = HibernateUtil.getSessionFactory().openSession();
                        return (AppUser) session.createQuery("from AppUser where username = :username").setParameter("username",username).uniqueResult();



            }

            @Override
            public void deleteByUsername(String username) {

            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends AppUser> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends AppUser> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<AppUser> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<Integer> integers) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public AppUser getOne(Integer integer) {
                return null;
            }

            @Override
            public AppUser getById(Integer integer) {
                return null;
            }

            @Override
            public AppUser getReferenceById(Integer integer) {
                return null;
            }

            @Override
            public <S extends AppUser> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends AppUser> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public <S extends AppUser> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public List<AppUser> findAll() {
                return null;
            }

            @Override
            public List<AppUser> findAllById(Iterable<Integer> integers) {
                return null;
            }

            @Override
            public <S extends AppUser> S save(S entity) {
                return null;
            }

            @Override
            public Optional<AppUser> findById(Integer integer) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Integer integer) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Integer integer) {

            }

            @Override
            public void delete(AppUser entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends Integer> integers) {

            }

            @Override
            public void deleteAll(Iterable<? extends AppUser> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public List<AppUser> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<AppUser> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends AppUser> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends AppUser> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends AppUser> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends AppUser> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends AppUser, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
    }

    @Bean
    public JwtTokenFilter getFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public UserService getUserService(){
            return new UserService();
    }



}