package it.red.algen.metasudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.service.AbstractApplicationService;

@Component
public class SudokuService extends AbstractApplicationService {
//	private static Logger logger = LoggerFactory.getLogger(SudokuService.class);

	@Autowired private MesEnvFactory envFactory;

	@Override
	protected String getApplicationName(){
		return "sudoku";
	}	
	@Override
	protected EnvFactory envFactory(){
		return envFactory;
	}
	
}
