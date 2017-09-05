package it.red.algen.metasudoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.red.algen.context.AlgorithmContext;
import it.red.algen.dataaccess.EnvFactory;
import it.red.algen.engine.SequenceRecombinator;
import it.red.algen.engine.SequenceMutator;
import it.red.algen.engine.StandardSelector;
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
	
	@Override
	protected void setupContext(AlgorithmContext context) {
		context.application.incubator = new MesIncubator();
		context.application.fitnessCalculator = new MesFitnessCalculator();
		context.application.fitnessCalculator.setup(context.application.incubator);
		context.application.selector = new StandardSelector();
		context.application.mutator = new SequenceMutator();
		context.application.recombinator = new SequenceRecombinator();
		context.application.solutionRenderer = new MesSolutionRenderer();

		context.application.selector.setup(context.parameters);
	}
	
}
