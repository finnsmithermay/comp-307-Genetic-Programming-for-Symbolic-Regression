import java.io.InputStreamReader;
import java.util.Scanner;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;



public class Regression {
	
	public static final int MAX_EVOLUTIONS = 200;
	
	public static final int DATA_SIZE = 20;
	public static final double MIN_ERROR = 0.25;

	
	public Variable variable;
	public create problem;
	public GPConfiguration config;
	public double x[];
	public double y[];
	
	
	public Regression(String file) {
		this.x = new double[DATA_SIZE];
		this.y = new double[DATA_SIZE];
		Scanner scan = new Scanner(new InputStreamReader(ClassLoader.getSystemResourceAsStream(file)));
		scan.nextLine(); //skip the first line as it is only the titles of the variables
		for (int i = 0; scan.hasNextDouble(); i++) {
		
			this.x[i] = scan.nextDouble();
			
			this.y[i] = scan.nextDouble();
		}
	}

	 
	public void initConfig() throws Exception {
		this.config = new GPConfiguration();
		this.config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
		this.config.setFitnessFunction(new FitnessFunction());
		this.config.setMaxCrossoverDepth(8);
		this.config.setMaxInitDepth(5);
		this.config.setPopulationSize(1000);
		this.config.setStrictProgramCreation(true);
		this.config.setReproductionProb(0.5f);
		this.config.setCrossoverProb(0.1f);
		this.config.setMutationProb(33.0f);

		this.variable = Variable.create(config, "X", CommandGene.DoubleClass);

		this.problem = new create(this.config, this.variable);
	}

	
	public void evolve() throws Exception {
		GPGenotype gp = this.problem.create();
		gp.setVerboseOutput(true);
		gp.evolve(MAX_EVOLUTIONS);
		gp.outputSolution(gp.getAllTimeBest());
		
		
	}


	public static void main(String[] args) throws Exception {
		
		Regression main = new Regression(args[0]);
			main.initConfig();
			main.evolve();
		
	}

	
	public class FitnessFunction extends GPFitnessFunction {
		
		
		public final Object[] noArgs = new Object[0];


		@Override
		protected double evaluate(IGPProgram igpProgram) {
			double totalError = 0;
			double sError = 0;
			for (int i = 0; i < DATA_SIZE; i++) {
				
				variable.set(x[i]);
				
					double result = igpProgram.execute_double(0, this.noArgs);
					double yError = y[i];
					totalError += Math.abs(result - yError);
					sError += totalError*totalError;

				
			}

			if (totalError < MIN_ERROR) {
				System.out.println(sError + "   --------");
				return 0;
			}
			//mse(totalError);
			return totalError;
		}
	}
	
	
	
}
