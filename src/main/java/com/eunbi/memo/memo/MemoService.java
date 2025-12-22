package com.eunbi.memo.memo;

import com.eunbi.memo.common.FileManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            String contents,
            MultipartFile imageFile
    ) {

        String imagePath = FileManager.saveFile(userId, imageFile);




        Memo memo = Memo.builder().userId(userId).title(title).contents(contents).imagePath(imagePath).build();

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

    public boolean updateMemo(long id, String title, String contents) {
        Optional<Memo> optionalMemo = memoRepository.findById(id);

        if(optionalMemo.isPresent()) {
            Memo memo = optionalMemo.get();
            memo = memo.toBuilder().title(title).contents(contents).build();
            try {
                memoRepository.save(memo);
            } catch (DataAccessException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    public boolean deleteMemo(long id) {
        Optional<Memo> optionalMemo = memoRepository.findById(id);
        if (optionalMemo.isPresent()) {
            Memo memo = optionalMemo.get();

            FileManager.removeFile(memo.getImagePath());

            try {
                memoRepository.delete(memo);
            } catch (DataAccessException e){
                return false;
            }

        } else {
            return false;
        }
        return true;
    }





}


