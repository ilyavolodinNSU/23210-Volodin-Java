// package factory.infrastructure.db.repositories.storage;

// import factory.core.entities.parts.Accessory;
// import factory.core.repository.IAccessoryRepository;
// import factory.infrastructure.storage.Storage;

// public class AccessoryRepositoryStorage implements IAccessoryRepository {
//     Storage<Accessory> storage = new Storage<>(5); // вместо этого может быть SQL etc...

//     @Override
//     public void push(Accessory car) {
//         storage.push(car);
//     }

//     @Override
//     public Accessory pop() {
//         return storage.pop();
//     }
// }