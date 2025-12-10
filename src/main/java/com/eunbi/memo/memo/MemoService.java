package com.eunbi.memo.memo;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public boolean createMemo(
            long userId,
            String title,
            String contents
    ) {
        Memo memo = Memo.builder().userId(userId).title(title).contents(contents).build();

        try {
            memoRepository.save(memo);
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    public List<Memo> getMemoList(long userId) {

        // WHERE `user_id` = #{}


        return memoRepository.findByUserId(userId, Sort.by("id").descending());
    }

    public Memo getMemo(long id) {
//        Optional<Memo> optionalMemo = memoRepository.findById(id);
//
//            return optionalMemo.get();

        return memoRepository.findById(id)
                .orElse(null);
        }
    }


