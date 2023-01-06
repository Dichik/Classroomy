import { Component, React } from 'react';
import Spinner from '../spinner/spinner.component';
import courseService from '../../services/course.service';

export default class CreateCourse extends Component {
    constructor(props) {
        super(props);

        this.onNameChange = this.onNameChange.bind(this);
        this.onDescriptionChange = this.onDescriptionChange.bind(this);
        this.onLoadingChange = this.onLoadingChange.bind(this);
        this.onErrChange = this.onErrChange.bind(this);

        this.state = {
            name: '',
            description: '',
            loading: false,
            err: ''
        };
    }

    onNameChange(e) {
        this.setState({
            name: e.target.value
        });
    }

    onDescriptionChange(e) {
        this.setState({
            description: e.target.value
        });
    }

    onLoadingChange() {
        this.setState({
            loading: !this.state.loading
        });
    }

    onErrChange(err) {
        this.setState({
            err: err
        });
    }

    handleSubmit = async () => {
        this.onLoadingChange();
        courseService
            .createCourse(this.state.name, this.state.description)
            .then((res) => res.data)
            .then((data) => {
                console.log(data);
            })
            .catch((err) => this.onErrChange(err.message))
            .finally(() => this.onLoadingChange());
    };

    render() {
        const loading = this.state.loading;
        const err = this.state.err;

        return (
            <div>
                {loading ? (
                    <Spinner />
                ) : (
                    <div>
                        {err && (
                            <h2
                                style={{
                                    color: 'red'
                                }}
                            >
                                {err}
                            </h2>
                        )}
                        <h1>lets create course</h1>
                        <form id="create-course" className="form-style">
                            <input
                                id="name"
                                className="input-border"
                                type="text"
                                placeholder="name"
                                value={this.state.name}
                                onChange={this.onNameChange}
                            />
                            <input
                                id="description"
                                className="input-border"
                                type="text"
                                placeholder="description"
                                value={this.state.description}
                                onChange={this.onDescriptionChange}
                            />
                            <button type="submit" onClick={this.handleSubmit}>
                                submit
                            </button>
                        </form>
                    </div>
                )}
            </div>
        );
    }
}
