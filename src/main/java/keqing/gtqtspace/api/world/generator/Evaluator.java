package keqing.gtqtspace.api.world.generator;

public interface Evaluator
{

    double evalNoise(double x);

    double evalNoise(double x, double y);

    double evalNoise(double x, double y, double z);
}