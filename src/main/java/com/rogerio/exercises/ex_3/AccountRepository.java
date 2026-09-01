package com.rogerio.exercises.ex_3;

public interface AccountRepository {
  Account findById(long id);
  void update(Account account);
}
