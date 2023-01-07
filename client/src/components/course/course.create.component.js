import { Component, React } from 'react';
import Spinner from '../spinner/spinner.component';
import courseService from '../../services/course.service';
import CheckButton from 'react-validation/build/button';
import Form from 'react-validation/build/form';
import { Input } from 'reactstrap';

const required = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const vname = (value) => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The name must be between 3 and 20 characters.
            </div>
        );
    }
};

const vdescription = (value) => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="alert alert-danger" role="alert">
                The description must be between 3 and 20 characters.
            </div>
        );
    }
};

export default class CreateCourse extends Component {
    constructor(props) {
        super(props);

        this.handleSubmit = this.handleSubmit.bind(this);
        this.onNameChange = this.onNameChange.bind(this);
        this.onDescriptionChange = this.onDescriptionChange.bind(this);
        this.onLoadingChange = this.onLoadingChange.bind(this);

        this.state = {
            name: '',
            description: '',
            loading: false,
            successful: false,
            message: ''
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

    handleSubmit(e) {
        e.preventDefault();

        this.form.validateAll();

        this.onLoadingChange;
        courseService
            .createCourse(this.state.name, this.state.description)
            .then((response) => {
                console.log(response.data);
                this.setState({
                    message: 'Created successfully',
                    successful: true
                });
            })
            .catch((error) => {
                this.onErrChange(error.message);
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                this.setState({
                    successful: false,
                    message: resMessage
                });
            })
            .finally(() => this.onLoadingChange);
    }

    render() {
        const loading = this.state.loading;

        return (
            <div>
                {loading ? (
                    <Spinner />
                ) : (
                    <div>
                        <h1>lets create course</h1>

                        <div className="col-md-12">
                            <div className="card card-container">
                                <Form
                                    onSubmit={this.handleSubmit}
                                    ref={(c) => {
                                        this.form = c;
                                    }}
                                >
                                    {!this.state.successful && (
                                        <div>
                                            <div className="form-group">
                                                <label htmlFor="name">
                                                    Name
                                                </label>
                                                <Input
                                                    type="text"
                                                    className="form-control"
                                                    name="name"
                                                    value={this.state.name}
                                                    onChange={this.onNameChange}
                                                    validations={[
                                                        required,
                                                        vname
                                                    ]}
                                                />
                                            </div>

                                            <div className="form-group">
                                                <label htmlFor="username">
                                                    Description
                                                </label>
                                                <Input
                                                    type="text"
                                                    className="form-control"
                                                    name="username"
                                                    value={
                                                        this.state.description
                                                    }
                                                    onChange={
                                                        this.onDescriptionChange
                                                    }
                                                    validations={[
                                                        required,
                                                        vdescription
                                                    ]}
                                                    style={{
                                                        height: '100px'
                                                    }}
                                                />
                                            </div>

                                            <div className="form-group">
                                                <button
                                                    className="btn btn-primary btn-block"
                                                    style={{
                                                        marginTop: '20px'
                                                    }}
                                                >
                                                    Create
                                                </button>
                                            </div>
                                        </div>
                                    )}

                                    {this.state.message && (
                                        <div className="form-group">
                                            <div
                                                className={
                                                    this.state.successful
                                                        ? 'alert alert-success'
                                                        : 'alert alert-danger'
                                                }
                                                role="alert"
                                            >
                                                {this.state.message}
                                            </div>
                                        </div>
                                    )}
                                    <CheckButton
                                        style={{ display: 'none' }}
                                        ref={(c) => {
                                            this.checkBtn = c;
                                        }}
                                    />
                                </Form>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        );
    }
}
