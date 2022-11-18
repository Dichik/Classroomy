import "./index.css"
import { useState } from "react"

export default function Search() {

    const [input, setInput] = useState('')

    const handleInput = (event) => {
        setInput(event.target.value)
    }

    const handleSubmit = () => {
        console.log(input)
    }

    return (
        <form className="search-box">
            <input value={input} type="text" placeholder="Search..."
                onChange={handleInput}
            />
            <button type='button' className='search-button'
                onClick={handleSubmit}
            />
        </form>
    )

}