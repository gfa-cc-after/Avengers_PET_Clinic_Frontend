export type PetProperties = {
  id: number
  name: string
  type: string
}
export type PetListProperty = {
  pets: PetProperties[]
}

export const PetsList = ({ pets }: PetListProperty) => {
  const arrayDataItems = pets.map((pet: PetProperties) => (
    <li key={pet.id}>
      <p>{pet.name}</p>
      <span>{pet.type}</span>
    </li>
  ))
  return (
    <>
      <h2>Pets list</h2>
      {pets.length ? <ul>{arrayDataItems}</ul> : "No pets yet..."}
    </>
  )
}
