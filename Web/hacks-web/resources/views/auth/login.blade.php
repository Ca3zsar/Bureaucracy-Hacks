@extends('layouts.auth')

@section('content')
    <section class="auth-section">
        <div class="auth-image-container">
            <div class="auth-image"></div>
        </div>
        <div class="auth-content ">
            <form class="login-form simple-form">
                <div class="row d-flex justify-content-center">
                    <div class="auth-item mb-4 col-md-9 col-12">
                        <input type="text" name="username" placeholder="username" class="input-form"/>
                    </div>
                </div>

                <div class="auth-item mb-4 col-md-9 col-12">
                    <input type="password" name="password" placeholder="password" class="input-form"/>
                </div>

                <div class="auth-item">
                    <button class="auth-button">login</button>
                </div>
                <div class="auth-item">
                    <button class="auth-button">register</button>
                </div>
            </form>
            <div class="auth-link-container">
                <a class="auth-link">Forgot password?</a>
            </div>
            <div class="auth-link-container">
                <a class="auth-link">Dont have an account?</a>
            </div>
        </div>
    </section>
@endsection
