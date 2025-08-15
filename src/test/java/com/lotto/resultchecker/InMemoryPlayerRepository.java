package com.lotto.resultchecker;

import com.lotto.domain.numbergenerator.WinningNumbers;
import com.lotto.domain.resultchecker.PlayerRepository;
import com.lotto.domain.resultchecker.Players;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryPlayerRepository implements PlayerRepository {
    private final Map<String, Players> players = new HashMap<>();

    @Override
    public <S extends Players> S save(S entity) {
        players.put(entity.hash(), entity);
        return entity;
    }

    @Override
    public <S extends Players> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Players> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Players> findAll() {
        return null;
    }

    @Override
    public Iterable<Players> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Players entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Players> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Players> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Players> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Players> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Players> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Players> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Players> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Players> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Players> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Players> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Players> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Players, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
