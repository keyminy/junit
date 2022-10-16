package site.metacoding.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.domain.Book;
import site.metacoding.domain.BookRepository;
import site.metacoding.web.dto.BookRespDto;
import site.metacoding.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@Service
public class BookService {
	
	private final BookRepository bookRepository;
	
	/* 1.책 등록 */
	//등록하는 작업이므로 적어줘,RuntimeException 발생시, Rollback
	@Transactional(rollbackFor = RuntimeException.class) 
	public BookRespDto 책등록하기(BookSaveReqDto dto) {
		Book bookPS = bookRepository.save(dto.toEntity());
		return new BookRespDto().toDTO(bookPS);
	}
	
	// 2.책 목록보기
	public List<BookRespDto> 책목록보기(){
		return bookRepository.findAll().stream()
							.map(bookPS -> new BookRespDto().toDTO(bookPS))
							.collect(Collectors.toList());
	}
	
	// 3.책 한건보기
	public BookRespDto 책한건보기(Long id) {
		Optional<Book> bookOP = bookRepository.findById(id);
		if(bookOP.isPresent()) {
			//찾았다면 꺼내서 return
			return new BookRespDto().toDTO(bookOP.get());
		}else {
			throw new RuntimeException("해당 아이디를 찾을 수 없습니다!!");
		}
	}
	
	// 4.책 삭제
	@Transactional(rollbackFor = RuntimeException.class)
	public void 책삭제하기(Long id) {
		bookRepository.deleteById(id);
	}
	
	// 5.책 수정
	@Transactional(rollbackFor = RuntimeException.class)
	public void 책수정하기(Long id,BookSaveReqDto dto) { //id,title,author받음
		//1.해당 id의 객체 찾기
		Optional<Book> bookOP = bookRepository.findById(id);
		if(bookOP.isPresent()) {
			Book bookPS = bookOP.get();
			bookPS.update(dto.getTitle(),dto.getAuthor());
		}else {
			throw new RuntimeException("해당 아이디를 찾을 수 없습니다!!");
		}
		//메서드 종료시 더티체킹(->db쪽으로 flush되요)으로 update됩니다.
	}
	
}
