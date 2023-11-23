package types

import "github.com/graphql-go/graphql"

// Product type definition
type Product struct {
	ID    int64   `json:"id"`
	Name  string  `json:"name"`
	Info  string  `json:"info,omitempty"`
	Price float64 `json:"price"`
}

// ProductType is the GraphQL schema for the product type
var ProductType = graphql.NewObject(graphql.ObjectConfig{
	Name: "Product",
	Fields: graphql.Fields{
		"id":    &graphql.Field{Type: graphql.Int},
		"name":  &graphql.Field{Type: graphql.String},
		"info":  &graphql.Field{Type: graphql.String},
		"price": &graphql.Field{Type: graphql.Float},
	},
},
)
