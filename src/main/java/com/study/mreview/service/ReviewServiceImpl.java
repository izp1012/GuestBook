package com.study.mreview.service;

import com.study.mreview.dto.ReviewDTO;
import com.study.mreview.entity.Movie;
import com.study.mreview.entity.Review;
import com.study.mreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {

        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(movieReview -> entityToDto(movieReview)).collect(Collectors.toList());

    }

    @Override
    public Long register(ReviewDTO movieReviewDTO) {

        Review movieReview = dtoToEntitiy(movieReviewDTO);

        reviewRepository.save(movieReview);

        return movieReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO movieReviewDTO) {

        Optional<Review> result = reviewRepository.findById(movieReviewDTO.getReviewnum());

        if(result.isPresent()){
            Review movieReview = result.get();
            movieReview.changeGrade(movieReview.getGrade());
            movieReview.changeText(movieReview.getText());

            reviewRepository.save(movieReview);
        }
    }

    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);
    }
}
