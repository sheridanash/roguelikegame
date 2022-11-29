function SignUp() {

    return (
        <div>
            <form className="row g-3">
                <div className="col-md-4">
                    <label for="validationServer01" className="form-label">First name</label>
                    <input type="text" className="form-control is-valid" id="validationServer01" value="Mark" required></input>
                        <div className="valid-feedback">
                            Looks good!
                        </div>
                </div>
                <div className="col-md-4">
                    <label for="validationServer02" className="form-label">Last name</label>
                    <input type="text" className="form-control is-valid" id="validationServer02" value="Otto" required></input>
                        <div className="valid-feedback">
                            Looks good!
                        </div>
                </div>
                <div className="col-md-4">
                    <label for="validationServerUsername" className="form-label">Username</label>
                    <div className="input-group has-validation">
                        <span className="input-group-text" id="inputGroupPrepend3">@</span>
                        <input type="text" className="form-control is-invalid" id="validationServerUsername" aria-describedby="inputGroupPrepend3 validationServerUsernameFeedback" required></input>
                            <div id="validationServerUsernameFeedback" className="invalid-feedback">
                                Please choose a username.
                            </div>
                    </div>
                </div>
                <div className="col-md-6">
                    <label for="validationServer03" className="form-label">E-Mail</label>
                    <input type="text" className="form-control is-invalid" id="validationServer03" aria-describedby="validationServer03Feedback" required></input>
                        <div id="validationServer03Feedback" className="invalid-feedback">
                            Please provide a valid e-mail.
                        </div>
                </div>
                <div className="col-md-3">
                    <label for="validationServer05" className="form-label">Password</label>
                    <input type="text" className="form-control is-invalid" id="validationServer05" aria-describedby="validationServer05Feedback" required></input>
                        <div id="validationServer05Feedback" className="invalid-feedback">
                            Please provide a valid password.
                        </div>
                </div>
                <div className="col-12">
                    <div className="form-check">
                        <input className="form-check-input is-invalid" type="checkbox" value="" id="invalidCheck3" aria-describedby="invalidCheck3Feedback" required></input>
                            <label className="form-check-label" for="invalidCheck3">
                                Agree to terms and conditions
                            </label>
                            <div id="invalidCheck3Feedback" className="invalid-feedback">
                                You must agree before submitting.
                            </div>
                    </div>
                </div>
                <div className="col-12">
                    <button className="btn btn-primary" type="submit">Submit form</button>
                </div>
            </form>
        </div>
    )

}

export default SignUp;