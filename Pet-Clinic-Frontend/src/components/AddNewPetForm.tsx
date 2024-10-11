import { type Dispatch, type SetStateAction, useState } from "react"
import { useNavigate } from "react-router-dom"
import { useAuth } from "../pages/AuthContext"
import type { PetProperties } from "./PetsList"

const backendUrl = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080"

type Props = {
  setRenderForm: (value: boolean) => void
  setParentError: Dispatch<SetStateAction<string | null>>
  setPets: React.Dispatch<React.SetStateAction<PetProperties[]>>
}

export const AddNewPetForm = ({
  setRenderForm,
  setParentError,
  setPets,
}: Props) => {
  const [name, setName] = useState("")
  const [type, setType] = useState("")
  const navigate = useNavigate()
  const { token } = useAuth()

  const sendToBackend = async (name: string, type: string) => {
    try {
      const response = await fetch(`${backendUrl}/api/pets/add`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ name, type }),
      })
      const responseBody = await response.json()
      if (!response.status.toString().startsWith("2")) {
        setParentError("Invalid name or type")
      } else {
        const newPet: PetProperties = { name, type, id: responseBody.id }
        setPets((previousPets) => [...previousPets, newPet])
        navigate("/pets")
      }
    } catch (error) {
      console.error("Error:", error)
      setParentError("Something is wrong. Please try it again.")
    }
  }

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    sendToBackend(name, type)
    setRenderForm(false)
  }

  return (
    <>
      <form className="formDiv" onSubmit={handleSubmit}>
        <h3 className="rf-title">Add new pet:</h3>
        <label className="label">Name:</label>
        <input
          className="input"
          type="text"
          name="name"
          value={name}
          onChange={(event) => setName(event.target.value)}
        />
        <label className="label">Type:</label>
        <input
          className="input"
          type="text"
          name="type"
          value={type}
          onChange={(event) => setType(event.target.value)}
        />
        <button className="btn" type="submit">
          Add
        </button>
      </form>
    </>
  )
}
