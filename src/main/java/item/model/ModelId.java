package item.model;

public class ModelId {
    public Long id;

    public static ModelId create(Long id) {
        ModelId modelId = new ModelId();
        modelId.id = id;
        return modelId;
    }
}
