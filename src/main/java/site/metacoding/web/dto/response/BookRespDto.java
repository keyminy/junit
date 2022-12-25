package site.metacoding.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.domain.Book;

@NoArgsConstructor
@Getter
public class BookRespDto {
	private Long id; //영속화된 데이터엔 id가 생김
	private String title;
	private String author;

	@Builder
	public BookRespDto(Long id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}

}
