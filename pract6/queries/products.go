package queries

import (
	"log"
	"pract6/db"
	"pract6/types"

	"github.com/graphql-go/graphql"
)

// GetProductByIdQuery http://localhost:8080/product?query={product(id:1){name,info,price}}
func GetProductByIdQuery() *graphql.Field {
	return &graphql.Field{
		Type:        types.ProductType,
		Description: "Get product by id",
		Args: graphql.FieldConfigArgument{
			"id": &graphql.ArgumentConfig{
				Type: graphql.Int,
			},
		},
		Resolve: func(p graphql.ResolveParams) (interface{}, error) {
			log.Printf("[query] get product by id\n")
			id, ok := p.Args["id"].(int)
			if ok {
				for _, product := range db.Products {
					if int(product.ID) == id {
						return product, nil
					}
				}
			}
			return nil, nil
		},
	}
}

// GetProductsQuery GetProducts http://localhost:8080/product?query={list{id,name,info,price}}
func GetProductsQuery() *graphql.Field {
	return &graphql.Field{
		Type:        graphql.NewList(types.ProductType),
		Description: "Get product list",
		Resolve: func(params graphql.ResolveParams) (interface{}, error) {
			log.Printf("[query] get product list\n")
			return db.Products, nil
		},
	}
}
