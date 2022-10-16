package site.metacoding.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.domain.Book;

@NoArgsConstructor
@Getter
public class BookRespDto {
	private Long id; //영속화된 데이터엔 id가 생김
	private String title;
	private String author;
	
	public BookRespDto toDTO(Book bookPS) {
		this.id = bookPS.getId();
		this.title = bookPS.getTitle();
		this.author = bookPS.getAuthor();
		return this;
	}
}
