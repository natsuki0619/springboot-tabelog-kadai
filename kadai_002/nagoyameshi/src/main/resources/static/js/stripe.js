const stripe = Stripe('pk_test_51P75VUHm9CqxMevCFYGIquHgyjlCaDux340fPx1fPO7AvXnruT2lGdZP8KaBxz6OXDxB4fzBp642VPzG1B2llmIT00HTfWYIJh');
 const paymentButton = document.querySelector('#paymentButton');
 paymentButton.addEventListener('click', () => {
	 console.log('Button clicked!');
	 stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });