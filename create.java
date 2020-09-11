import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Pow;



public class create extends GPProblem {

	private GPConfiguration config;
	private Variable xVariable;


	public create(GPConfiguration config, Variable xVariable) throws InvalidConfigurationException {
		super(config);
		this.config = config;
		this.xVariable = xVariable;
	}


	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		Class[] types = { CommandGene.DoubleClass };
		
		Class[][] argTypes = { {}, };
		
		CommandGene[][] nodeSets = { { 
			xVariable, 
			
				new Pow(this.config, CommandGene.DoubleClass),
				new Multiply(this.config, CommandGene.DoubleClass), 
				new Add(this.config, CommandGene.DoubleClass),
				new Divide(this.config, CommandGene.DoubleClass), 
				new Subtract(this.config, CommandGene.DoubleClass),
				new Terminal(this.config, CommandGene.DoubleClass, -1.0d, 10.0d, true) } };

		GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);
		return result;
	}
}
