

<?php $__env->startSection('content'); ?>
<section class="auth-section">
    <div class="auth-image-container">
        <div class="auth-image"></div>
    </div>
    <div class="auth-content ">
        <form class="login-form simple-form" action="/login" method=>
            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="text" name="username" placeholder="username" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="password" name="password" placeholder="password" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="password" name="confirm password" placeholder="confirm password" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="email" name="email" placeholder="email" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">

                <div class="auth-item mb-4 col-12 col-lg-7">
                    <input type="text" name="city" placeholder="city" class="input-form" />
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="mb-4 col-12 col-lg-7 ">
                    <input type="checkbox" class="check-box" ><span>I agree to the terms & conditions</span>
                </div>
            </div>

            <div class="row d-flex justify-content-center align-items-center">
                <div class="auth-item mb-4 col-12 col-lg-7">
                    <button class="auth-button">Create account</button>
                </div>
            </div>

            <div class="auth-link-container">
                <a class="auth-link" href="/login">I already have an account</a>
            </div>

    </div>
</section>
<?php $__env->stopSection(); ?>
<?php echo $__env->make('layouts.auth', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH D:\Facultate\Anul 2 - semestrul II\IP\IpProject\Bureaucracy-Hacks\Web\hacks-web\resources\views/auth/register.blade.php ENDPATH**/ ?>