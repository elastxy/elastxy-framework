package it.red.algen.metasudoku;

import org.springframework.stereotype.Component;

import it.red.algen.service.AbstractApplicationService;

@Component
public class SudokuService extends AbstractApplicationService {
//	private static Logger logger = LoggerFactory.getLogger(SudokuService.class);

	@Override
	protected String getApplicationName(){
		return "sudoku";
	}	
	
}
