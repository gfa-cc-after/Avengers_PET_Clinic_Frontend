import { Fragment } from "react/jsx-runtime"

export type PetProperties = {
  id: number
  name: string
  type: string
}

const Pet = ({ id, name, type }: PetProperties) => {
  return (
    <Fragment key={id}>
      <ul key={id}>
        {name} {type}
      </ul>
    </Fragment>
  )
}

export { Pet }
