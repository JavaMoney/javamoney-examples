package net.java.javamoney.examples.tradingapp.repo;

import java.util.List;

import net.java.javamoney.examples.tradingapp.domain.Member;

public interface MemberDao
{
    public Member findById(Long id);

    public Member findByEmail(String email);

    public List<Member> findAllOrderedByName();

    public void register(Member member);
}
