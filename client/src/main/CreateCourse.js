import { React, useState } from 'react';
import Spinner from '../spinner/Spinner';

export default function CreateCourse() {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [loading, setLoading] = useState(false);
    const [err, setErr] = useState('');

    const handleSubmit = async () => {
        setLoading(true);
        const course = {
            name: name,
            description: description
        };
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            body: JSON.stringify(course)
        };
        try {
            const response = await fetch(
                `http://localhost:8080/courses`,
                requestOptions
            );
            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('result is', data);
        } catch (err) {
            setErr(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            {loading ? (
                <Spinner />
            ) : (
                <div>
                    {err && <h2>{err}</h2>}
                    <h1>lets create course</h1>
                    <form>
                        <input
                            id="name"
                            type="text"
                            placeholder="name"
                            onChange={(e) => setName(e.target.value)}
                        />
                        <input
                            id="description"
                            type="text"
                            placeholder="description"
                            onChange={(e) => setDescription(e.target.value)}
                        />
                        <button type="submit" onClick={handleSubmit}>
                            submit
                        </button>
                    </form>
                </div>
            )}
        </div>
    );
}
