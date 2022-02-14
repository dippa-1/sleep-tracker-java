import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.EmbeddingLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.conf.preprocessor.FeedForwardToRnnPreProcessor;
import org.deeplearning4j.nn.conf.preprocessor.RnnToFeedForwardPreProcessor;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.ArrayList;

public class Trainer {
  private MultiLayerNetwork network;

  public Trainer() {
    // TODO: real network architecture
    MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .activation(Activation.RELU)
            .list()
            .layer(new EmbeddingLayer.Builder().nIn(3).nOut(5).build())
            .layer(new LSTM.Builder().nIn(5).nOut(7).activation(Activation.TANH).build())
            .layer(new RnnOutputLayer.Builder(LossFunctions.LossFunction.MCXENT).nIn(7).nOut(3).activation(Activation.SOFTMAX).build())
            .inputPreProcessor(0, new RnnToFeedForwardPreProcessor())
            .inputPreProcessor(1, new FeedForwardToRnnPreProcessor())
            .build();

    network = new MultiLayerNetwork(conf);
    network.init();
  }

  public void train(ArrayList<SleepEntry> entries) {
    // TODO: train network
    int batchSize = 3;
    int timeSeriesLength = 8;
    INDArray input = Nd4j.create(batchSize, 1, timeSeriesLength);
    INDArray outLabels = Nd4j.create(batchSize, 4, timeSeriesLength);

    for (int i = 0; i < entries.size(); i++) {
      SleepEntry entry = entries.get(i);
      input.putScalar(new int[]{i, 0, 0}, entry.getRestRating());
    }

    network.setInput(input);
    network.setLabels(outLabels);

    network.computeGradientAndScore();
    System.out.println(network.score());
  }
}
